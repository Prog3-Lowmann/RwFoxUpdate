package viewControler;

public class Command {
    public final Operator operator;

    public Command(String text) {
        String input=text.substring(0,2);
        switch (input){
            case ":c":
                this.operator=Operator.INSERT;
                break;
            case ":d":
                this.operator=Operator.REMOVE;
                break;
            case ":r":
                this.operator=Operator.READ;
                break;
            case ":u":
                this.operator=Operator.INSPECT;
                break;
            case ":p":
                this.operator=Operator.PERSISTENCE;
                break;
            case ":e":
                this.operator=Operator.QUIT;
                break;
            default:
                this.operator=Operator.ERROR;
                break;
        }

    }
}
