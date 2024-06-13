import java.nio.file.Paths;
import java.util.Random;

public class Bonus extends HangMan
{
    private static boolean isWin = false;
    private static int recordCorrectAnswer =0;

    public static boolean getNewChance() throws InterruptedException
    {
        int answer  = randomNumber() ;
        int playTime = 1 ;
        /**/
        boolean isTrue = false;
        player.loadBackgroundMusic("guessNumer",Paths.get("music/guessNumberBackGroundMusic.wav").toString());
        player.playBackGround("guessNumer",6);
        GUI.bonusgreeting();
        while(!isTrue && playTime < 11)
        {  
            String inPutNumber = keyBoard.nextLine();
            try 
            {
                String inPutNumbers[] = inPutNumber.split("");
                String answers[] = Integer.toString(answer).split("");

                if(answer < 1000 )
                {
                    String[] newAnswers = {"0",answers[0],answers[1],answers[2]};
                    answers = newAnswers;
                }
                
                if(inPutNumbers.length<4)
                {
    
                    System.out.println("> you enter less than 4 numbers");
                }
                else if(inPutNumbers.length>4 )
                {
                    System.out.println("> you enter more than 4 numbers");
                }
                else if(inPutNumbers[0].equals(inPutNumbers[1])||inPutNumbers[0].equals(inPutNumbers[2])||inPutNumbers[0].equals(inPutNumbers[3])||inPutNumbers[1].equals(inPutNumbers[2])||inPutNumbers[1].equals(inPutNumbers[3])||inPutNumbers[2].equals(inPutNumbers[3]))
                {
                    System.out.println("> you enter repeat number");
                }
                else
                {
                    if(answer == Integer.parseInt(inPutNumber))
                    {   
                        Thread.sleep(1500);
                        isTrue = true ;
                        isWin = true;
                    }
                    else
                    {
                        int A = howManyA(inPutNumbers , answers);
                        int B = howManyB(inPutNumbers , answers);

                        if(score(A,B) > recordCorrectAnswer){
                            player.loadSoundEffect("guessRight", Paths.get("music/guessRight.wav").toString());
                            player.play("guessRight");
                            recordCorrectAnswer = score(A, B);
                        }
                        else if(score(A,B) < recordCorrectAnswer){
                            player.loadSoundEffect("guessWrong", Paths.get("music/guessWrong.wav").toString());
                            player.play("guessWrong",6);
                            recordCorrectAnswer = score(A, B);
                        }
                        System.out.println("========================================================================");
                        System.out.println(playTime + " " +inPutNumber+" : "+A+" A "+B+" B ");
                        playTime++ ;
                    }
                }

            } 
            catch (NumberFormatException e) 
            {
                //System.out.println("You enter wrong " + inPutNumber);
            }
        }
        player.stop("guessNumer");;
        return isWin;
    }


    public static int randomNumber()
    {
        Random random = new Random();
        int totalRandomNumbers = 0 ;
        int randomNumbers[] = new int[4];
        boolean usedNumber[] = new boolean[10];
        for(int i = 0 ; i < 4 ; i++ )
        {
            int newNumber ;
            do
            {
                newNumber = random.nextInt(10);
            }
            while(usedNumber[newNumber]);
            randomNumbers[i] = newNumber ;
            usedNumber[randomNumbers[i]] = true ;
        }
        totalRandomNumbers = (randomNumbers[0]*1000+randomNumbers[1]*100+randomNumbers[2]*10+randomNumbers[3]*1);
        return totalRandomNumbers ;
    }

    public static int howManyA(String inPutNumbers[] ,String answers[])
    {
        int a = 0 ;
        for(int i = 0 ; i < 4 ; i++)
        {
            if(inPutNumbers[i].equals(answers[i]))
            {   
                a++ ;
            }
        }
        
        return a;
    }

    public static int howManyB(String inPutNumbers[] ,String answers[])
    {
        int b = 0 ;
        for(int i = 0 ; i < 4 ; i++)
        {
            for(int j = 0 ; j < 4 ; j++)
            {
                if( i != j && inPutNumbers[i].equals(answers[j]))
                {
                    b++ ;
                }
            }
        }
        
        return b;
    }

    private static int score(int A, int B){

        int scoreCorrectAnswer = A + B;
        return scoreCorrectAnswer;

    }
}
