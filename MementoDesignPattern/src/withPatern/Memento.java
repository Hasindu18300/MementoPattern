package withPatern;

import java.util.Stack;

//Memento Class
class State {
    private final String state;

    public State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}

//originator
class TextEditor {
    private String content = "";

    public void type(String words) {
        content += words;
    }

    public String getContent() {
        return content;
    }

    public State save() {
        return new State(content);
    }

    public void restore(State state) {
        content = state.getState();
    }

}

class CareTaker {
    private Stack<State> history = new Stack<State>();

    public void save(TextEditor editor) {
        history.push(editor.save());
    }

    public void undo(TextEditor editor) {
        if (!history.isEmpty()) {
            editor.restore(history.pop());
        }
    }
}

public class Memento {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CareTaker careTaker = new CareTaker();

        editor.type("Hello World");
        careTaker.save(editor);

        editor.type("Hii Bro");
        careTaker.save(editor);

        System.out.println("Content: " + editor.getContent());

        careTaker.undo(editor);
        System.out.println("1st undo: " + editor.getContent());

        careTaker.undo(editor);
        System.out.println("2nd undo: " + editor.getContent());
    }
}
