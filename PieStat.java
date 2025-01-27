/**
 * Gurveer Singh 
 * Student No.= 3187474
 */ 
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class PieStat
{
    public static void main (String[] args) throws FileNotFoundException
    {
        Scanner f = new Scanner(new File("A1Data.txt"));
        Scanner ff = new Scanner(new File("A1Data.txt"));
        Scanner kb=new Scanner(System.in);
        
        int count=0;
        //StringBuilder result= new StringBuilder();
        
        //Scanner line = new Scanner(f.nextLine()); 
        //System.out.println(f.next());

        count= linesCount(ff,count);
        
        String names[] = new String[count];
        int bib[]= new int[count];
        int pieTimes[][]= new int[count][10];
        int pieVolumes[][] = new int[count][3];
        
        double avgTime[] = new double[count];
        double avgVol[] = new double[count];
        double weightedScore[] = new double[count];
        int ranks[]= new int[count];

        fillStartingData(f,count,names,bib,pieTimes,pieVolumes);

        calculateAverageTime(pieTimes,count,avgTime);

        calculateAverageVolume(pieVolumes,count,avgVol);

        calculateWeightedScore(avgTime,avgVol,weightedScore,count);

        calculateRank(weightedScore,count,ranks);

        System.out.println("PPEA Individual Statistics \n*********************************\n");
        displaySummary(names,bib,pieTimes,pieVolumes,count, avgTime,avgVol,weightedScore,ranks);
        System.out.println("\n******* Edit Records*******");
        System.out.println("\nChoose an option:\nA - Add a score\nB - Quit");
        String option=kb.next();
        // 

        while(option.equalsIgnoreCase("a"))
        {

            System.out.println(option);
            System.out.println("\nEnter the bib number of the competitor to edit.");
            int userBib = kb.nextInt(); 
            int validIndex = getIndexToEdit(userBib,bib,count);

            while(validIndex==-1)
            {
                System.out.println("Number not found.");
                System.out.println("\nEnter the bib number of the competitor to edit.");
                userBib = kb.nextInt(); 
                validIndex = getIndexToEdit(userBib,bib,count);
            }

            System.out.println("\nSelect:\nT: add a time\nV: add a volume");
            String optionEdit=kb.next();
            addScore(optionEdit,validIndex,pieTimes,count,avgTime,avgVol,weightedScore,ranks,pieVolumes,kb);

            displaySummary(names,bib,pieTimes,pieVolumes,count, avgTime,avgVol,weightedScore,ranks);
            System.out.println("****************************************************");
            System.out.println("\nChoose an option:\nA - Add a score\nB - Quit");
            option=kb.next();
        }
        System.out.println("\nend of program");

    }
    
    public static int linesCount(Scanner ff,int count)
    {
        while(ff.hasNextLine())
        {
            count=count+1; 
            ff.nextLine();
        }
        return count;
    }

   

    

    public static void fillStartingData(Scanner f,int count,String names[],int bib[], int pieTimes[][],int pieVolumes[][])
    {
        int x=0;
        while(f.hasNext())
        {
            names[x]= f.next();
            bib[x]= f.nextInt();
            for(int j=0;j<10;j++)
            {
                pieTimes[x][j] = f.nextInt();
            }
            for(int k=0;k<3;k++)
            {
                pieVolumes[x][k] = f.nextInt();
            }
            x++;
        }
    }

    public static void calculateAverageTime(int pieTimes[][],int count, double avgTime[])
    {
        double avTime=0;

        for(int i=0;i<count;i++)
        {
            avTime=0;
            int minIndex= 0;
            int maxIndex= 0;

            for(int j=0;j<10;j++)
            {
                if(pieTimes[i][j]>pieTimes[i][maxIndex])
                {
                    maxIndex=j;
                }
                if(pieTimes[i][j]<pieTimes[i][minIndex])
                {
                    minIndex= j;
                }
            }
            for(int k=0;k<10;k++)
            {
                if(k!=maxIndex && k!=minIndex)
                {
                    avTime= avTime + pieTimes[i][k];
                }
            }
            avTime=avTime/8;
            avgTime[i]=avTime;
        }
    }

    public static void calculateAverageVolume(int pieVolumes[][],int count, double avgVol[])
    {
        double avVol=0;
        for(int i=0;i<count;i++)
        {
            avVol=0;
            for(int j=0;j<3;j++)
            {
                avVol = avVol + (double)pieVolumes[i][j];
            }
            avVol = avVol/3;
            avgVol[i] = avVol;
        }
    }

    public static void calculateWeightedScore(double avgTime[],double avgVol[],double weightedScore[],int count)
    {
        double wScore=0;
        for(int i=0;i<count;i++)
        {
            wScore=0;
            wScore = (30- avgTime[i]) + (avgVol[i]/35);
            weightedScore[i] = wScore;
        }
    }

    public static void calculateRank(double weightedScore[],int count,int ranks[])
    {
        int counter;
        double currentMax=0;
        for(int i=0;i<count;i++)
        {
            counter=1;
            for(int j=0;j<count;j++)
            {
                if(weightedScore[i]<weightedScore[j])
                {
                    counter++;
                }
            }
            ranks[i]=counter;
        }

    }
     public static int getIndexToEdit(int userBib, int bib[],int count)
    {
        int validIndex = -1;
        for(int i=0;i<count;i++)
        {
            if(userBib==bib[i])
            {
                validIndex=i;
            }
        }

        return validIndex;
    }

    public static void addScore(String optionEdit,int validIndex,int pieTimes[][],int count,double avgTime[],double avgVol[],double weightedScore[],int ranks[],int pieVolumes[][],Scanner sc)
    {
        if(optionEdit.equalsIgnoreCase("t"))
        {
            System.out.println("\nEnter the new score value.");
            int score=sc.nextInt();

            for(int j=0;j<=8;j++)
            {
                pieTimes[validIndex][j]=pieTimes[validIndex][j+1];                      
            }
            pieTimes[validIndex][9]=score;
            calculateAverageTime(pieTimes,count,avgTime);
            calculateWeightedScore(avgTime,avgVol,weightedScore,count);
            calculateRank(weightedScore,count,ranks);
        }
        else if(optionEdit.equalsIgnoreCase("v"))
        {
            System.out.println("\nEnter the new score value.");
            int score=sc.nextInt();

            for(int j=0;j<2;j++)
            {
                pieVolumes[validIndex][j]=pieVolumes[validIndex][j+1];                            
            }
            pieVolumes[validIndex][2]=score;
            calculateAverageVolume(pieVolumes,count,avgVol);
            calculateWeightedScore(avgTime,avgVol,weightedScore,count);
            calculateRank(weightedScore,count,ranks);
        }
        System.out.println("\nScore Added.\n");

    }

    public static void displaySummary(String names[],int bib[], int pieTimes[][],int pieVolumes[][], int count, double avgTime[],double avgVol[],double weightedScore[],int ranks[])
    {
        int x1=0;
        System.out.println("Name\tBib#\tAvgTime\tV1\tV2\tV3\tAvgVol\tScore\tRank");
        while(x1<count)
        {
            System.out.print(names[x1]+"\t");
            System.out.print(+bib[x1]+"\t");
            System.out.print(String.format("%.2f", avgTime[x1])+"\t");

            /**for(int j=7;j<10;j++)
            {
            System.out.print("\t"+pieTimes[x1][j]+" ");
            }
             */
            for(int j=0;j<3;j++)
            {
                System.out.print(pieVolumes[x1][j]+"\t");
            }

            System.out.print(String.format("%.2f",avgVol[x1])+"\t");
            System.out.print(String.format("%.2f", weightedScore[x1])+"\t");
            System.out.print( ranks[x1]+"\t");

            System.out.println();
            x1++;
        }

    }

}
