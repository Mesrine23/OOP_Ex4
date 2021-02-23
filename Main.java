import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;


public class Main
{

    public static void main(String[] args)
    {
        Student[] st = new Student[36];
        Teacher[] tchr = new Teacher[18];
        // READING STUDENTS
        try
        {
            File file = new File("/home/users1/sdi1800140/studentsFULL.txt");
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine())
            {
                String data = sc.nextLine();
                String[] parts = data.split("-");
                String name = parts[0];
                String f = parts[1];
                String c = parts[2];
                int fl = Integer.parseInt(f);
                int cl = Integer.parseInt(c);
                //System.out.println(name);
                //System.out.println(name + " ~ " + fl + " ~ " + cl);
                st[i] = new Student(name,fl,cl);
                ++i;
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Error with Students");
        }
        // READING TEACHERS
        try
        {
            File file = new File("/home/users1/sdi1800140/teachersFULL.txt");
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine())
            {
                String data = sc.nextLine();
                String[] parts = data.split("-");
                String name = parts[0];
                String f = parts[1];
                String c = parts[2];
                int fl = Integer.parseInt(f);
                int cl = Integer.parseInt(c);
                //System.out.println(name);
                //System.out.println(name + " ~ " + fl + " ~ " + cl);
                tchr [i] = new Teacher(name,fl,cl);
                ++i;
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Error with Teachers");
        }
        /* END OF DATA READING */

        int CL = Integer.parseInt(args[0]);
        int Lj = Integer.parseInt(args[1]);
        int Ls = Integer.parseInt(args[2]);
        int Lt = Integer.parseInt(args[3]);
        int N = Integer.parseInt(args[4]);

        /*int CL=2;
        int Lj=1;
        int Ls=2;
        int Lt=3;
        int N=6;*/

        School school = new School(CL);

        int guess;
        int choose;
        int all=0;
        while(all != (CL+1)*18)
        {
            guess = new Random().nextInt(2);
            if(guess == 0) // student
            {
                choose = new Random().nextInt(CL*18);
                if(st[choose].getAt() != "OFF")
                    continue;
                else
                {
                    st[choose] = school.enter(st[choose]);
                    ++all;
                }
            }
            else // teacher
            {
                choose = new Random().nextInt(18);
                if(tchr[choose].getFlag()==1)
                    continue;
                else
                {
                    tchr[choose] = school.place(tchr[choose]);
                    ++all;
                }
            }
        }

        school.operate(N,Lj,Ls,Lt);

        school.print();

        school.empty();

        System.out.println("\n");

    }
}


abstract class Person {
    private String name;
    private int fl;
    private int cl;
    private int fatigue;

    public Person (String nam, int f, int c)
    {
        name = nam;
        fl = f;
        cl = c;
        fatigue = 0;
    }
    public void printNAME() { System.out.print(name); }
    public int getFl() { return fl; }
    public int getCl() { return cl; }
    public int getFatigue() { return fatigue; }
    public void setFatigue(int N, int L) { fatigue = N*L; }
}

class Student extends Person {
    private String at;
    public String type;

    public Student(String nam, int f, int c)
    {
        super(nam,f,c);
        at = "OFF";
        if(c<=3)
        {
            type = "Junior";
            System.out.println("A new Junior Student has been created");
            System.out.println(nam + " -> Floor No." + f + ", Class No." + c + "\n");
        }
        else {
            type = "Senior";
            System.out.println("A new Senior Student has been created");
            System.out.println(nam + " -> Floor No." + f + ", Class No." + c + "\n");
        }
    }
    public void printNAME() { super.printNAME(); }
    public void setAt(String there) { at = there; }
    public String getAt() { return at; }
    public int getFl() { return super.getFl(); }
    public int getCl() { return super.getCl(); }
    public int getFatigue() { return super.getFatigue(); }
    public void setFatigue(int N, int L) { super.setFatigue(N,L); }
}

class Teacher extends Person {
    private int flag;

    public Teacher(String nam, int f, int c)
    {
        super(nam,f,c);
        flag = 0;
        System.out.println("A new Teacher has been created");
        System.out.println(nam + " -> Floor No." + f + ", Class No." + c + "\n");
    }
    public void printNAME() { super.printNAME(); }
    public void setFlag(int f) { flag = f; }
    public int getFlag() { return flag; }
    public int getFl() { return super.getFl(); }
    public int getCl() { return super.getCl(); }
    public int getFatigue() { return super.getFatigue(); }
    public void setFatigue(int N, int L) { super.setFatigue(N,L); }
}

/*abstract class Place {
    private Student st;
}*/

class Schoolyard {
    // no private members
    // no need for specific constructor
    public void enter(Student st)
    {
        st.printNAME();
        System.out.println(" enters Schoolyard");
        st.setAt("Schoolyard");
    }
    public void exit(Student st)
    {
        st.setAt("Schoolyard");
        st.printNAME();
        System.out.println(" exits schoolyard");
    }
}

class Stairs {
    // no private members
    // no need for specific constructor
    public void enter(Student st)
    {
        st.printNAME();
        System.out.println(" exits Schoolyard and enters Stairs");
        st.setAt("Stairs");
    }
    public void exit(Student st)
    {
        st.setAt("Stairs");
        st.printNAME();
        System.out.println(" exits stairs");
    }
}

class Corridor {
    // no private members
    // no need for specific constructor
    public void enter(Student st)
    {
        st.printNAME();
        System.out.println(" enters Corridor from his Floor");
        st.setAt("Corridor");
    }
    public void exit(Student st)
    {
        st.setAt("Corridor");
        st.printNAME();
        System.out.println(" exits corridor");
    }
}

class Class {
    private Student[] list;
    private Teacher teacher;
    private int ppl; // counter for students

    public Class (int i)
    {
        ppl = 0;
        list = new Student[i];
    }
    public Student enter(Student st)
    {
        st.printNAME();
        System.out.println(" exits Corridor and enters Class");
        st.setAt("Class");
        list[ppl] = st;
        ++ppl;
        return st;
    }
    public Teacher place(Teacher tchr)
    {
        teacher = tchr;
        tchr.printNAME();
        System.out.println(" is placed in his Class");
        tchr.setFlag(1);
        return tchr;
    }
    public void operate(int N, int L, int Lt)
    {
        teacher.setFatigue(N,Lt);
        for(int i=0 ; i < ppl ; ++i)
            list[i].setFatigue(N,L);
    }
    public void print()
    {
        System.out.print("The teacher is: ");
        teacher.printNAME();
        System.out.println(" (" + teacher.getFatigue() + " fp)");
        for(int i=0 ; i<ppl ; ++i)
        {
            list[i].printNAME();
            System.out.println(" (" + list[i].getFatigue() + " fp)");
        }
    }
    public Student exit()
    {
        --ppl;
        list[ppl].printNAME();
        System.out.println(" stars exiting...");
        list[ppl].printNAME();
        System.out.println(" exits classroom...");
        Student st = list[ppl];
        list[ppl] = null;
        return st;
    }
    public Teacher teacher_out()
    {
        Teacher tchr;
        tchr = teacher;
        teacher = null;
        tchr.printNAME();
        System.out.println(" exits classroom");
        return tchr;
    }
}

class Floor {
    private int ClNum; // number of students in each class
    private Class[] cl;
    private Corridor corr;

    public Floor (int i)
    {
        ClNum = i;
        corr = new Corridor();
        cl = new Class[6];
        for (int j=0 ; j<6 ; ++j)
            cl[j] = new Class(i);
    }
    public Student enter(Student st, int c)
    {
        st.printNAME();
        System.out.println(" exits Stairs and enters Floor");
        st.setAt("Floor");
        corr.enter(st);
        st = cl[c-1].enter(st);
        return st;
    }
    public Teacher place(Teacher tchr, int c)
    {
        tchr = cl[c-1].place(tchr);
        return tchr;
    }
    public void operate(int N, int Lj, int Ls, int Lt)
    {
        for(int i=0; i<6 ; ++i)
        {
            if(i<3)
                cl[i].operate(N,Lj,Lt);
            else
                cl[i].operate(N,Ls,Lt);
        }
    }
    public void print()
    {
       for (int i=0 ; i<6 ; ++i)
       {
           System.out.println("Class No." + (i+1));
           cl[i].print();
           System.out.println();
       }
    }
    public Student exit(int i)
    {
        Student st;
        if((i/ClNum)==0)
            st = cl[0].exit();
        else if((i/ClNum)==1)
            st = cl[1].exit();
        else if((i/ClNum)==2)
            st = cl[2].exit();
        else if((i/ClNum)==3)
            st = cl[3].exit();
        else if((i/ClNum)==4)
            st = cl[4].exit();
        else
            st = cl[5].exit();

        corr.exit(st);
        st.printNAME();
        System.out.println(" exits Floor");
        return st;
    }
    public Teacher teacher_out(int i)
    {
        Teacher tchr;
        if(i==0)
            tchr = cl[0].teacher_out();
        else if(i==1)
            tchr = cl[1].teacher_out();
        else if(i==2)
            tchr = cl[2].teacher_out();
        else if(i==3)
            tchr = cl[3].teacher_out();
        else if(i==4)
            tchr = cl[4].teacher_out();
        else
            tchr = cl[5].teacher_out();

        return tchr;
    }
}

class School {
    private int STppl;
    private int TCHRppl;
    private int ClNum;
    private Schoolyard yard;
    private Stairs stair;
    private Floor[] fl;

    public School (int i)
    {
        STppl = i*18;
        TCHRppl = 18;
        ClNum = i;
        yard = new Schoolyard();
        stair = new Stairs();
        fl = new Floor[3];
        for(int j=0 ; j<3 ; ++j)
            fl[j] = new Floor(i);
        System.out.println("A School had been created!\n\n");
    }
    public Student enter(Student st)
    {
        st.printNAME();
        System.out.println(" enters School");
        st.setAt("School");
        yard.enter(st);
        stair.enter(st);
        int f = st.getFl();
        int c = st.getCl();
        st = fl[f-1].enter(st,c);
        return st;
    }
    public Teacher place(Teacher tchr)
    {
        tchr.printNAME();
        System.out.println(" has entered School");
        int f = tchr.getFl();
        int c = tchr.getCl();
        tchr = fl[f-1].place(tchr,c);
        return tchr;
    }
    public void operate(int N, int Lj, int Ls, int Lt)
    {
        for(int i=0; i<3 ; ++i)
            fl[i].operate(N,Lj,Ls,Lt);
    }
    public void print()
    {
        System.out.println("\nSchool life consists of:\n\n");
        for(int i=0 ; i<3 ; ++i)
        {
            System.out.println("Floor No." + (i+1) + " contains:");
            System.out.println("---------------------");
            fl[i].print();
            System.out.println("\n");
        }
    }
    public void teacher_out(Teacher tchr)
    {

    }
    public void empty()
    {
        // loop for all students
        for (int i=0 ; i<STppl ; ++i)
        {
            Student st;
            if(i<(STppl/3))
                st = fl[0].exit(i);
            else if (i<(STppl*2)/3)
                st = fl[1].exit(i-(ClNum*6));
            else
                st = fl[2].exit(i-(ClNum*12));

            stair.exit(st);
            yard.exit(st);
            st.setAt("School");
            st.printNAME();
            System.out.println(" exits school");
        }

        //loop for all teachers
        for(int i=0 ; i<18 ; ++i)
        {
            Teacher tchr;
            if(i<6)
                tchr = fl[0].teacher_out(i);
            else if(i<12)
                tchr = fl[1].teacher_out(i-6);
            else
                tchr = fl[2].teacher_out(i-12);

            tchr.setFlag(0);
            tchr.printNAME();
            System.out.println(" exits school");
        }
        System.out.println();
        System.out.println("School is empty!");

    }
}
