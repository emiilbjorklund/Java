public enum Constants{
    WIDTH(35), // With of Pasture
    HEIGHT(24), // Height of Pasture
    NO_FENCES(5), // Number of random fences
    NO_PLANTS(60), // Number of plants
    NO_SHEEPS(15), // Number of sheeps
    NO_WOLFS(5), // Number of wolfs
    MATURE_SHEEPS(100), // Time before a sheep can reproduce for the first time
    MATURE_WOLFS(100), // Time before a wolf can reproduce for the first time
    DELAY_SHEEPS(80), // Time before a sheep can reproduce
    DELAY_WOLFS(80), // Time before a wolf can reproduce
    DEATH_SHEEPS(200), // Time a sheep can live without food
    DEATH_WOLFS(500), // Time a wolf can live without food
    SPEED_SHEEP(10), // Number of operations a sheep can do per tick
    SPEED_WOLF(8), // Number of operations a wolf can do per tick
    FOV_SHEEPS(1), //Field of view for sheeps (1 means from -1 to 1, 2 means from -2 to 2)
    FOV_WOLFS(1); // Field of view for wolfs

    private int value;

    Constants(final int newValue) {
        value = newValue;
    }

    public void set(int value){
        this.value = value;
    }

    public int get() { return value; }
}