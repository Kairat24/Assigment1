import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class Person{
    String name;
    String surname;
    int age;
    boolean gender;

    public Person(String name, String surname, int age, boolean gender){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
    }


    @Override
    public String toString(){
        String gen = gender?"male":"female";
        return "Hi, I am " + name + " " + surname + ", a " + age + "-years-old " + gen + ". ";
    }
}

class Student extends Person{
    int studentId;
    ArrayList<Integer> grades;

    public Student(String name, String surname, int age, boolean gender, int studentId) {
        super(name, surname, age, gender);
        this.studentId = studentId;
        grades = new ArrayList<>();
    }

    public void addGrade(int grade){
        grades.add(grade);
    }

    public double calculateGPA(){
        double sum = 0;
        for(int i=0;i<grades.size();i++){
            sum+=grades.get(i);
        }
        return sum/grades.size();
    }

    @Override
    public String toString(){
        return super.toString() + "I am a student with ID " + studentId;
    }

}

class Teacher extends Person{
    String subject;
    int yearsOfExperience;
    int salary;
    public Teacher(String name, String surname, int age, boolean gender, String subject, int yearsOfExperience, int salary) {
        super(name, surname, age, gender);
        this.subject = subject;
        this.yearsOfExperience = yearsOfExperience;
        this.salary = salary;
    }

    public void giveRaise(double percent){
        salary += salary * (percent/100);
    }

    @Override
    public String toString(){
        return super.toString() + "I teach " + subject;
    }
}

class School{
    ArrayList<Person> members;
    public School(){
        members = new ArrayList<>();
    }

    public void addMember(Person person){
        members.add(person);
    }

    @Override
    public String toString(){
        String allMembers = "";
        for(int i=0;i<members.size();i++){
            allMembers += members.get(i).toString() + "\n";
        }
        return allMembers;
    }
}



public class Assigment {
    public static void main(String[] args) throws IOException {
        School school = new School();

        BufferedReader bf = new BufferedReader(new FileReader("students.txt"));
        String line;
        while((line = bf.readLine()) != null){
            String [] data = line.split(",");
            String name = data[0];
            String lastname = data[1];
            int age = Integer.parseInt(data[2]);
            boolean gender = data[3].equals("Male");
            int id = Integer.parseInt(data[4]);
            Student student = new Student(name, lastname, age, gender, id);
            for(int i=5; i < data.length;i++){
                student.addGrade(Integer.parseInt(data[i]));
            }
            school.addMember(student);
        }

        bf.close();

        bf = new BufferedReader(new FileReader("teachers.txt"));
        while((line = bf.readLine()) != null){
            String [] data = line.split(",");
            String name = data[0];
            String lastname = data[1];
            int age = Integer.parseInt(data[2]);
            boolean gender = data[3].equals("Male");
            String subject = data[4];
            int experience = Integer.parseInt(data[5]);
            int salary = Integer.parseInt(data[6]);
            Teacher teacher = new Teacher(name, lastname, age, gender, subject, experience, salary);
            if(experience > 10){
                teacher.giveRaise(10);
            }
            school.addMember(teacher);

        }

        for(Person member: school.members){
            if(member instanceof Student){
                Student student = (Student) member;
                System.out.println(student + ", GPA: " + student.calculateGPA());
            }
            else if(member instanceof Teacher){
                Teacher teacher = (Teacher) member;
                System.out.println(teacher + ", Salary: " + teacher.salary);
            }
        }


    }


}