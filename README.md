# Memento Design Pattern - Complete Study Guide

## 📚 Overview

The **Memento Design Pattern** is a behavioral design pattern that allows you to capture and restore an object's internal state without violating encapsulation. It's essentially a "snapshot" mechanism that enables undo/redo functionality.

## 🎯 Purpose

- **Capture State**: Save an object's state at a specific point in time
- **Restore State**: Roll back to a previously saved state
- **Maintain Encapsulation**: Access internal state without exposing implementation details
- **Undo/Redo Operations**: Enable users to revert or repeat actions

## 🏗️ Components

### 1. **Memento (State)**
- Stores the internal state of the Originator
- Immutable object that acts as a snapshot
- Provides controlled access to the saved state

```java
class State {
    private final String state;
    
    public State(String state) {
        this.state = state;
    }
    
    public String getState() {
        return state;
    }
}
```

### 2. **Originator**
- The object whose state needs to be saved
- Creates mementos containing snapshots of its current state
- Restores its state from a memento

```java
class TextEditor {
    private String content = "";
    
    public void type(String words) {
        content += words;
    }
    
    // Creates a memento
    public State save() {
        return new State(content);
    }
    
    // Restores from memento
    public void restore(State state) {
        content = state.getState();
    }
    
    public String getContent() {
        return content;
    }
}
```

### 3. **Caretaker**
- Manages and stores mementos
- Doesn't modify or examine memento contents
- Handles the history of states (Stack for undo/redo)

```java
class CareTaker {
    private Stack<State> history = new Stack<>();
    
    public void save(TextEditor editor) {
        history.push(editor.save());
    }
    
    public void undo(TextEditor editor) {
        if (!history.isEmpty()) {
            editor.restore(history.pop());
        }
    }
}
```

## 🔄 Pattern Flow

1. **Save State**: Originator creates a memento with current state
2. **Store Memento**: Caretaker stores the memento in history
3. **Change State**: Originator's state changes through operations
4. **Restore State**: When needed, Caretaker provides memento to Originator
5. **Apply Restoration**: Originator restores its state from the memento

## 💡 Real-World Examples

### Basic Text Editor (Simple Undo)
```java
public class BasicExample {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CareTaker careTaker = new CareTaker();
        
        editor.type("Hello World");
        careTaker.save(editor);
        
        editor.type(" - Java Programming");
        System.out.println("Current: " + editor.getContent());
        // Output: Hello World - Java Programming
        
        careTaker.undo(editor);
        System.out.println("After Undo: " + editor.getContent());
        // Output: Hello World
    }
}
```

### Advanced Code Editor (Undo/Redo)
```java
class HistoryManager {
    private Stack<EditorState> history = new Stack<>();
    private Stack<EditorState> redoStack = new Stack<>();
    
    public void save(Editor editor) {
        history.push(editor.Save());
        redoStack.clear(); // Clear redo history on new save
    }
    
    public void undo(Editor editor) {
        if (!history.isEmpty()) {
            redoStack.push(editor.Save()); // Save current for redo
            editor.Restore(history.pop());
        }
    }
    
    public void redo(Editor editor) {
        if (!redoStack.isEmpty()) {
            history.push(editor.Save()); // Save current for undo
            editor.Restore(redoStack.pop());
        }
    }
}
```

## ✅ Advantages

1. **Encapsulation**: Internal object details remain hidden
2. **Flexibility**: Easy to add undo/redo functionality
3. **State Management**: Clean separation of state storage logic
4. **History Tracking**: Maintains complete operation history
5. **Rollback Capability**: Quick restoration to previous states

## ❌ Disadvantages

1. **Memory Overhead**: Each memento consumes memory
2. **Performance Impact**: Creating mementos can be expensive
3. **Storage Complexity**: Managing large histories can be challenging
4. **Cleanup Required**: Old mementos need proper garbage collection

## 🎯 When to Use

### ✅ **Use Memento Pattern When:**
- You need undo/redo functionality
- State restoration is required
- You want to maintain state history
- Encapsulation must be preserved
- Rollback operations are common

### ❌ **Avoid When:**
- Memory usage is critical concern
- State objects are very large
- Frequent state changes occur
- Simple solutions suffice

## 🆚 Without vs With Pattern

### Without Pattern (Problems)
```java
// Problems: Direct state access, no encapsulation
class TextEditor {
    private String content;
    
    // Violates encapsulation
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

// Client code handles state management
String saved = editor.getContent(); // Manual save
editor.setContent(saved); // Manual restore
```

### With Pattern (Solution)
```java
// Clean separation, proper encapsulation
class TextEditor {
    private String content = "";
    
    public State save() { return new State(content); }
    public void restore(State state) { content = state.getState(); }
}

// Dedicated state management
CareTaker careTaker = new CareTaker();
careTaker.save(editor); // Clean save
careTaker.undo(editor); // Clean restore
```

## 🔧 Implementation Tips

### 1. **Immutable Mementos**
```java
class State {
    private final String content; // final for immutability
    
    public State(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content; // Safe to return as String is immutable
    }
}
```

### 2. **Memory Management**
```java
class CareTaker {
    private Stack<State> history = new Stack<>();
    private final int MAX_HISTORY = 50; // Limit history size
    
    public void save(TextEditor editor) {
        if (history.size() >= MAX_HISTORY) {
            history.removeElementAt(0); // Remove oldest
        }
        history.push(editor.save());
    }
}
```

### 3. **Deep vs Shallow Copy**
```java
class ComplexState {
    private final List<String> items;
    
    public ComplexState(List<String> items) {
        // Deep copy for mutable objects
        this.items = new ArrayList<>(items);
    }
    
    public List<String> getItems() {
        return new ArrayList<>(items); // Return copy
    }
}
```

## 🏷️ Related Patterns

- **Command Pattern**: Often used together for undo/redo
- **Prototype Pattern**: For copying object states
- **State Pattern**: Different approach to state management
- **Flyweight Pattern**: For optimizing memory usage of mementos

## 📝 Key Points for Exam

1. **Three Components**: Memento, Originator, Caretaker
2. **Encapsulation**: Memento provides controlled access to state
3. **Immutability**: Mementos should be immutable
4. **Memory Consideration**: Can consume significant memory
5. **Use Cases**: Undo/Redo, Checkpoints, State restoration
6. **Stack Structure**: Typically uses Stack for history management
7. **Behavioral Pattern**: Focuses on object state and behavior

## 💻 Complete Working Example

```java
// Full implementation with all components
public class MementoPatternDemo {
    public static void main(String[] args) {
        Editor editor = new Editor();
        HistoryManager history = new HistoryManager();
        
        // Initial typing
        editor.write("Java ");
        history.save(editor);
        
        editor.write("Design ");
        history.save(editor);
        
        editor.write("Patterns");
        System.out.println("Final: " + editor.getCode());
        // Output: Java Design Patterns
        
        // Undo operations
        history.undo(editor);
        System.out.println("Undo 1: " + editor.getCode());
        // Output: Java Design
        
        history.undo(editor);
        System.out.println("Undo 2: " + editor.getCode());
        // Output: Java
        
        // Redo operation
        history.redo(editor);
        System.out.println("Redo: " + editor.getCode());
        // Output: Java Design
    }
}
```

---

## 🎓 Study Checklist

- [ ] Understand the three components and their roles
- [ ] Know when to use vs when to avoid the pattern
- [ ] Understand encapsulation benefits
- [ ] Be able to implement basic undo/redo functionality
- [ ] Know memory management considerations
- [ ] Understand relationship with other patterns
- [ ] Practice with different examples (Text Editor, Game States, etc.)

**Good luck with your exam! 🚀**
