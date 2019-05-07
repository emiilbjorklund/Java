public enum IsCompatible {

    FENCE(false), PLANT(false), HERBIVORE(false), CARNIVORE(false);

    private boolean value;

    IsCompatible(boolean newValue) {
        value = newValue;
    }

    public boolean check(Entity entity){
        switch(this){
            case FENCE:
                return false;
            case PLANT:
                if ((entity instanceof Animal == true) || (entity instanceof Fence == true)){
                return false;
                }
                else 
                    return true;
            
            case HERBIVORE:
                if ((entity instanceof Animal == true) || (entity instanceof Fence == true)){
                    return false;
                }
                else 
                    return true;
            
            case CARNIVORE:
                if ((entity instanceof Carnivore == true) || (entity instanceof Fence == true)){
                    return false;
                }
                else 
                    return true;
            default:
                return false;
        }
    }
}