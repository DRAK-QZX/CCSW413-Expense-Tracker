package model;

public class ButtonHandler {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void clickButton() {
        if (command != null) {
            command.execute();
        }
    }
}