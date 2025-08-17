package WithoutPackage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class TextEditor {
    private String content;

    public void type(String words) {
        this.content += words;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

public class Main {
    public static void main(String[] args) {

        TextEditor doc1 = new TextEditor();
        doc1.type("Hello");
        doc1.type("JAva");

        String saved = doc1.getContent();
        System.out.println("Before Undo: " + saved);

        doc1.setContent(saved);
        System.out.println("After Undo: " + doc1.getContent());
    }
}