# Ember MVC Framework

**Ember MVC** is a lightweight, annotation-based Model-View-Controller (MVC) framework for building Java web applications. It simplifies request handling, validation, and routing with a minimal setup, making it ideal for developers seeking a flexible yet powerful solution.

**Version**: 1.0.0  
**License**: MIT  
**Documentation**: [Javadoc](https://sandratraniaina.github.io/ember-mvc/)  
**Releases**: [GitHub Releases](https://github.com/sandratraniaina/ember-mvc/releases)

## Features

- **Annotation-Based Routing**: Use `@Get`, `@Post`, and `@Url` to map HTTP requests to methods.
- **Validation**: Built-in annotations like `@Numeric`, `@Required`, and `@Length` for input validation (See the [Javadoc](https://sandratraniaina.github.io/ember-mvc/) for more validation).
- **Flexible Response Handling**: Return `ModelView` objects for views or JSON for REST APIs.
- **Session Management**: Built-in support for session handling using the `Session` class.
- **Role-Based Access Control**: Secure endpoints with the `@RequiredRole` annotation.
- **File Uploads**: Easy file handling with built-in support for multipart requests.
- **Lightweight**: Minimal dependencies (Servlet API, Gson).

## Installation

### Prerequisites

- Java 17 or higher
- A servlet container (e.g., Apache Tomcat 10.x)
- Git (optional, for cloning)

### Steps

1. **Download the JAR**:
   - Get `ember-mvc-1.0.0.jar` from the [Releases](https://github.com/sandratraniaina/ember-mvc/releases) page.
   - Alternatively, clone and build from source:

     ```bash
     git clone https://github.com/sandratraniaina/ember-mvc.git
     cd ember-mvc
     ./build.bat  # This script builds the project into a jar file
     ```

2. **Add to Your Project**:
   - Copy `ember-mvc-1.0.0.jar` to your project's `WEB-INF/lib/` directory.
   - Ensure dependencies are included:
     - `jakarta.servlet-api-6.0.0.jar` (provided by your servlet container).
     - `gson-2.10.1.jar` (download from [Maven Central](https://mvnrepository.com/artifact/com.google.code.gson/gson)).

3. **Configure Your Servlet Container**:
   - See "Project Configuration" below.

## Project Configuration

Configure Ember MVC in your web application via `web.xml` or annotations (if using a Servlet 3.0+ container).

### Using `web.xml`

Add the following to `WEB-INF/web.xml`:

```xml
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
         version="6.0">

    <!-- FrontController Servlet -->
    <servlet>
        <servlet-name>FrontController</servlet-name>
        <servlet-class>mg.emberframework.core.FrontController</servlet-class>
        <load-on-startup>1</load-on-startup>
        <init-param>
            <param-name>package_name</param-name>
            <param-value>mg.myapp.controllers</param-value> <!-- Your controller package -->
        </init-param>
        <multipart-config /> <!-- Enable file uploads -->
    </servlet>
    <servlet-mapping>
        <servlet-name>FrontController</servlet-name>
        <url-pattern>/</url-pattern> <!-- Route all requests -->
    </servlet-mapping>
</web-app>
```

### Initialization Parameters

- `package_name`: The package containing your controllers (required).
- `error_param_name`: Attribute name for validation errors (optional, defaults to `"errors"`).
- `error_redirection_param_name`: Parameter for error redirect URL (optional, defaults to `"error-url"`).
- `role_attribute_name`: Session attribute for user roles (optional, defaults to `"role"`).
- `custom_error_page`: Custom error page display (optional, defaults to `Ember MCV` error page).

## Usage Examples

### Example 1: Basic Controller

Create a controller to handle a GET request:

```java
package mg.myapp.controllers;

import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.data.ModelView;

@Url("/home")
public class HomeController {
    @Get
    public ModelView index() {
        ModelView mv = new ModelView();
        mv.setUrl("index.jsp");
        mv.addObject("message", "Welcome to Ember MVC!");
        return mv;
    }
}
```

- **Request**: `GET /home`
- **Result**: Renders `index.jsp` with `message` available as a request attribute.

**`index.jsp`**:

```jsp
<html>
<body>
    <h1>${message}</h1>
</body>
</html>
```

### Example 2: REST API Endpoint

Return JSON data with `@RestApi`:

```java
package mg.myapp.controllers;

import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.RestApi;
import mg.emberframework.annotation.http.Url;

import mg.emberframework.core.data.ModelView;

@Url("/api/users")
public class UserController {
    @Get
    @RestApi
    @Url
    public ModelView getUsers() {
        ModelView mv = new ModelView();

        mv.addObject("users", new String[]{"Alice", "Bob"});

        return mv;
    }
}
```

- **Request**: `GET /api/users`
- **Response**: `{data:{users:["Alice", "Bob"]}}` (JSON).

### Example 3: Form Validation

Validate form input with annotations:

```java
package mg.myapp.controllers;

import mg.emberframework.annotation.http.Post;
import mg.emberframework.annotation.http.RequestParameter;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.annotation.validation.Numeric;
import mg.emberframework.annotation.validation.Required;
import mg.emberframework.core.data.ModelView;

@Url("/submit")
public class FormController {
    @Post
    public ModelView processForm(
        @RequestParameter("name") @Required String name,
        @RequestParameter("age") @Numeric int age
    ) {
        ModelView mv = new ModelView();
        mv.setUrl("result.jsp");
        mv.addObject("name", name);
        mv.addObject("age", age);
        return mv;
    }
}
```

- **Request**: `POST /submit` with `name=John&age=25`
- **Result**: Renders `result.jsp` if valid; otherwise, errors are set in the `errors` attribute.

### Example 4: Model Validation

Use validation annotations directly on model classes:

```java
package model;

import mg.emberframework.annotation.validation.Numeric;
import mg.emberframework.annotation.validation.Required;

public class User {
    @Required
    private String name;
    
    @Numeric
    private int age;
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
```

Controller to handle user data:

```java
package controller;

import mg.emberframework.annotation.http.Controller;
import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.data.ModelView;
import model.User;
import winter.data.annotation.http.RequestParam;

@Controller
@Url("/users")
public class UserController {
    @Url("/user")
    @Get
    public ModelView showUser(@RequestParam("user") User user) {
        ModelView mv = new ModelView();
        mv.setUrl("user-details.jsp");
        mv.addObject("user", user);
        return mv;
    }
}
```

JSP page to display validation errors:

```jsp
<%@ page import ="java.util.*" %>
<%@ page import ="model.*" %>
<%@ page import ="mg.emberframework.core.data.*" %>
<%
    ModelValidationResults result = (ModelValidationResults)request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>
<body>
    <h1>User Registration</h1>
    <form action="./users/user" method="get">
        <div>
            <label for="user.name">Name:</label>
            <input type="text" name="user.name" id="name" value="<%= result.getFieldExceptionValue("user.name") %>">
            <%= result.getFieldExceptionMessage("user.name") %>
        </div>
        <div>
            <label for="user.age">Age:</label>
            <input type="text" name="user.age" id="age" value="<%= result.getFieldExceptionValue("user.age") %>">
            <%= result.getFieldExceptionMessage("user.age", "<br>") %>
        </div>
        <input type="hidden" name="error-url" value="/user-form">
        <button type="submit">Submit</button>
    </form>
</body>
</html>
```

### Example 5: Session Management

Use the `Session` class for persistent data across requests:

```java
package controller;

import mg.emberframework.annotation.http.Controller;
import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.Post;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.Session;
import mg.emberframework.core.data.ModelView;
import mg.emberframework.annotation.http.RequestParameter;
import model.User;

@Controller
@Url("/auth")
public class AuthController {
    // Method 1: Inject Session as parameter
    @Post
    @Url("/login")
    public ModelView login(
        @RequestParameter("username") String username,
        @RequestParameter("password") String password,
        Session session
    ) {
        ModelView mv = new ModelView();
        
        // Authentication logic here
        if (authenticateUser(username, password)) {
            User user = getUserData(username);
            // Store user in session
            session.add("currentUser", user);
            session.add("role", "ADMIN");
            mv.setUrl("dashboard.jsp");
        } else {
            mv.setUrl("login.jsp");
            mv.addObject("error", "Invalid credentials");
        }
        
        return mv;
    }
    
    // Method 2: Session as controller field with getter/setter
    private Session session;
    
    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    @Get
    @Url("/profile")
    public ModelView viewProfile() {
        ModelView mv = new ModelView();
        
        // Get user from session
        User user = (User) session.get("currentUser");
        
        if (user != null) {
            mv.setUrl("profile.jsp");
            mv.addObject("user", user);
        } else {
            mv.setUrl("login.jsp");
            mv.addObject("error", "Please log in first");
        }
        
        return mv;
    }
    
    @Get
    @Url("/logout")
    public ModelView logout() {
        // Clear session
        session.clear();
        
        ModelView mv = new ModelView();
        mv.setUrl("login.jsp");
        mv.addObject("message", "You have been logged out");
        return mv;
    }
    
    // Helper methods
    private boolean authenticateUser(String username, String password) {
        // Authentication logic
        return true; // Dummy implementation
    }
    
    private User getUserData(String username) {
        // Get user data from database
        User user = new User();
        user.setName(username);
        user.setAge(30);
        return user;
    }
}
```

### Example 6: Role-Based Access Control

Use the `@RequiredRole` annotation to restrict access to specific roles:

```java
package controller;

import mg.emberframework.annotation.http.Controller;
import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.RequiredRole;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.data.ModelView;

@Controller
@Url("/admin")
@RequiredRole(values = {"ADMIN"}) // Restrict entire controller to ADMIN role
public class AdminController {
    
    @Get
    @Url("/dashboard")
    public ModelView dashboard() {
        ModelView mv = new ModelView();
        mv.setUrl("admin/dashboard.jsp");
        mv.addObject("title", "Admin Dashboard");
        return mv;
    }
    
    @Get
    @Url("/users")
    public ModelView manageUsers() {
        ModelView mv = new ModelView();
        mv.setUrl("admin/users.jsp");
        mv.addObject("title", "User Management");
        return mv;
    }
    
    @Get
    @Url("/reports")
    @RequiredRole(values = {"ADMIN", "ANALYST"}) // Allow both ADMIN and ANALYST roles
    public ModelView viewReports() {
        ModelView mv = new ModelView();
        mv.setUrl("admin/reports.jsp");
        mv.addObject("title", "Reports");
        return mv;
    }
}
```

```java
package controller;

import mg.emberframework.annotation.http.Controller;
import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.RequiredRole;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.data.ModelView;

@Controller
@Url("/user-management")
public class UserManagementController {
    
    // Accessible to all users
    @Get
    @Url("/view-profile")
    public ModelView viewProfile() {
        ModelView mv = new ModelView();
        mv.setUrl("user/profile.jsp");
        return mv;
    }
    
    // Only accessible to users with MANAGER role
    @Get
    @Url("/team-members")
    @RequiredRole(values = {"MANAGER"})
    public ModelView viewTeamMembers() {
        ModelView mv = new ModelView();
        mv.setUrl("user/team.jsp");
        return mv;
    }
    
    // Only accessible to users with either ADMIN or MANAGER roles
    @Get
    @Url("/department-stats")
    @RequiredRole(values = {"ADMIN", "MANAGER"})
    public ModelView viewDepartmentStats() {
        ModelView mv = new ModelView();
        mv.setUrl("user/department-stats.jsp");
        return mv;
    }
}
```

**Note**: The role value is checked against the session attribute specified by the `role_attribute_name` in the servlet configuration (defaults to "role").

### Example 7: File Uploads

Handle file uploads using the `File` class:

```java
package controller;

import mg.emberframework.annotation.http.Controller;
import mg.emberframework.annotation.http.Post;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.data.ModelView;
import mg.emberframework.core.File;
import winter.data.annotation.http.RequestParam;

@Controller
@Url("/upload")
public class FileUploadController {
    @Post
    @Url
    public ModelView uploadFile(@RequestParam("profile_picture") File file) {
        ModelView mv = new ModelView();
        
        try {
            // Get the file bytes
            byte[] data = file.getBytes();
            
            // Save to disk (example)
            String savePath = "/path/to/uploads/";
            file.writeTo(savePath);
            
            mv.addObject("success", "File uploaded successfully");
            mv.addObject("fileName", fileName);
            mv.addObject("fileType", contentType);
            mv.addObject("fileSize", size);
        } catch (Exception e) {
            mv.addObject("error", "Error uploading file: " + e.getMessage());
        }
    
        mv.setUrl("upload-result.jsp");
        return mv;
    }
}
```

HTML form for file upload:

```html
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
</head>
<body>
    <h1>Upload Profile Picture</h1>
    <form action="./upload" method="post" enctype="multipart/form-data">
        <div>
            <label for="profile_picture">Select Image:</label>
            <input type="file" name="profile_picture" id="profile_picture">
        </div>
        <input type="hidden" name="error-url" value="/upload-form">
        <button type="submit">Upload</button>
    </form>
</body>
</html>
```

### Example 8: Custom Error Page

Configure a custom error page to handle exceptions gracefully instead of the default Ember MVC error page:

#### Configuration

Add the `custom_error_page` parameter in `web.xml`:

```xml
<servlet>
    <servlet-name>FrontController</servlet-name>
    <servlet-class>mg.emberframework.core.FrontController</servlet-class>
    <load-on-startup>1</load-on-startup>
    <init-param>
        <param-name>package_name</param-name>
        <param-value>mg.myapp.controllers</param-value>
    </init-param>
    <init-param>
        <param-name>custom_error_page</param-name>
        <param-value>/error.jsp</param-value> <!-- Custom error page location -->
    </init-param>
    <multipart-config />
</servlet>
```

#### Custom Error Page (`error.jsp`)

Create `error.jsp` in your webapp root to display error details:

```jsp
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error - Ember MVC</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1 { color: #d9534f; }
        .status { font-size: 1.2em; color: #5bc0de; }
        .message { font-weight: bold; }
        pre { background: #f8f8f8; padding: 10px; border: 1px solid #ddd; overflow-x: auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Error Occurred</h1>
        <p class="status">Status Code: <%= request.getAttribute("status-code") %></p>
        <p class="message">Message: <%= request.getAttribute("message") %></p>
        <h3>Details:</h3>
        <pre>
            <% 
                Exception e = (Exception) request.getAttribute("exception");
                if (e != null) {
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </pre>
        <p><a href="/">Return to Home</a></p>
    </div>
</body>
</html>
```

#### Controller to Trigger an Error

Test the custom error page with a controller that throws an exception:

```java
package mg.myapp.controllers;

import mg.emberframework.annotation.http.Get;
import mg.emberframework.annotation.http.Url;
import mg.emberframework.core.data.ModelView;

@Url("/test")
public class TestController {
    @Get
    @Url("/error")
    public ModelView triggerError() throws Exception {
        // Simulate an error
        throw new Exception("This is a test error!");
    }
}
```

- **Request:** `GET /test/error`
- **Result:** Redirects to `error.jsp`, displaying the status code (500), message ("This is a test error!"), and stack trace.

**Note:** The `ExceptionHandler` forwards to the custom error page if `custom_error_page` is set, overriding the default HTML error page. Attributes like `exception`, `status-code`, and `message` are available in the request scope for use in your JSP.

## Contributing

Contributions are welcome! Please:

- Fork the repo.
- Create a feature branch (`git checkout -b feature/my-feature`).
- Submit a pull request.

## License

Released under the MIT License. See [LICENSE](LICENSE) for details.
