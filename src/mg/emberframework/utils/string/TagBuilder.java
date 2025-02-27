package mg.emberframework.utils.string;

public class TagBuilder {
    private TagBuilder() {
    }

    public static String enclose(String text, String tag) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String unclosedTag(String tag) {
        return "<" + tag + ">";
    }

    public static String bold(String text) {
        return enclose(text, "strong");
    }

    public static String heading(String text, int level) throws IllegalArgumentException {
        if (level < 0 || level > 6) {
            throw new IllegalArgumentException("Invalid heading level");
        }
        String tag = "h" + level;
        return enclose(text, tag);
    }
    
    public static String paragraph(String text) {
        return enclose(text, "p");
    }
}
