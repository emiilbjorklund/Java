// Tobias Isaksson
// tobbe.isaksson@gmail.com
// OOP med Java våren 2019
// Uppgift 4

/**
*   Klass att hålla alla parametrar när de skickas mellan
*   klassen för inmatning och själva simuleringen
*   Här sätts även defaultvärden.
*/
public class Parameters
{
    // Parametrar för får
    public int ageSheepDuplicate = 150; // Antal tick innan får förökar sig
    public int sheepduplicateRate = 85; // Antal tick mellan förökning
    public int numberOfSheep = 22;      // Antal får
    public int sheepSpeed = 10;         // Antal tick innan fåret flyttar sig
    public int sheepMaxTimeEat = 100;   // Max antal klick utan mat
    public int sheepLengthOfVision = 2; // Hur långt ser entity

    // Parametrar för vargar
    public int ageWolfDuplicate = 200;  // Antal tick innan varg förökar sig
    public int wolfduplicateRate = 90;  // Antal tick mellan förökning
    public int numberOfWolf = 4;        // Antal vargar
    public int wolfSpeed = 7;           // Antal tick innan varg flyttar sig
    public int wolfMaxTimeEat = 150;    // Max antal klick utan mat
    public int wolfLengthOfVision = 2;  // Hur långt ser entity

    // Parametrar för gräs
    public int agePlantDuplicate = 40;  // Hur fort förökar sig gräs
    public int plantDuplicateRate = 21; // Hur ofta förökar sig gräs
    public int numberOfPlants = 16;     // Hur mycket gräs skall det finnas fråns start
  
    // Storlek på hagen
    public int   width = 35;
    public int   height = 24;

    
    /**
    /   Validera att inmatade parametrar ligger inom tillåtna gränser
    */
    public boolean validate()
    {
        if (ageSheepDuplicate < 1 || ageSheepDuplicate > 900) return false;
        if (sheepduplicateRate < 1 || sheepduplicateRate > 900) return false;
        if (numberOfSheep < 1 || numberOfSheep > 900) return false;
        if (sheepSpeed < 1 || sheepSpeed > 900) return false;
        if (sheepMaxTimeEat < 1 || sheepMaxTimeEat > 900) return false;
        if (sheepLengthOfVision < 1 || sheepLengthOfVision > 5) return false;

        if (ageWolfDuplicate < 1 || ageWolfDuplicate > 900) return false;
        if (wolfduplicateRate < 1 || wolfduplicateRate > 900) return false;
        if (numberOfWolf < 1 || numberOfWolf > 900) return false;
        if (wolfSpeed < 1 || wolfSpeed > 900) return false;
        if (wolfMaxTimeEat < 1 || wolfMaxTimeEat > 900) return false;
        if (wolfLengthOfVision < 1 || wolfLengthOfVision > 5) return false;

        if (agePlantDuplicate < 1 || agePlantDuplicate > 900) return false;
        if (plantDuplicateRate < 1 || plantDuplicateRate > 900) return false;
        if (numberOfPlants < 1 || numberOfPlants > 900) return false;

        if (width < 5 || width > 50) return false;
        if (height < 5 || height > 50) return false;
        
        return true;
    }
    
}