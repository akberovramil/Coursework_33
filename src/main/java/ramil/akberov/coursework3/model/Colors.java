package ramil.akberov.coursework3.model;

public enum Colors {

    BlACK("Черные"), WHITE("Белые"), RED("Красные"), YELLOW("Желтые"), GREEN("Зеленые"), BROWN("Коричневые");

    private String name;

    Colors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
