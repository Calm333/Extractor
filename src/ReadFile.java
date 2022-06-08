import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile {

    public String readers(String s) {
        StringBuilder concat = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(s))) {

            String temp;

            while ((temp = reader.readLine()) != null) {
                concat.append(temp).append('\n');
            }

        } catch (IOException e) {
            System.out.println(e);

        }
        return concat.toString();
    }

    public String extractEmail(String str3) {
        StringBuilder concat = new StringBuilder();
        Matcher matcher = Pattern.compile("[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+").matcher(str3);
        while (matcher.find()) {
            concat.append(matcher.group()).append(" ").append('\n');
        }
        return concat.toString().trim();
    }

    public String extractPhone(String str3) {
        StringBuilder concat = new StringBuilder();
        Matcher matcher = Pattern.compile("((8|\\+7)[\\- ]?)?(\\(?\\d{2,4}\\)?[\\- ]?)?[\\d\\- ]{7,10}").matcher(str3);
        while (matcher.find()) {
            concat.append(matcher.group()).append(" ").append('\n');
        }
        return concat.toString().trim();
    }

    public String extractURL(String str3) {
        StringBuilder concat = new StringBuilder();
        Matcher matcher =
                Pattern.compile("(?:(?:https?|ftps?)://)?[\\w/\\-?=%.]+\\.[\\w/\\-&?=%.]+").matcher(str3);
        while (matcher.find()) {
            concat.append(matcher.group()).append(" ").append('\n');
        }
        return concat.toString().trim();
    }

    public String clean(StringBuilder stringBuilder) {
        if (stringBuilder.toString().isEmpty()) {
            return stringBuilder.toString();
        }
        stringBuilder.delete(0, stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
