import { UniversityYear } from "./university-year.model";

export class Student {
    id: number;
    firstName: string;
    lastName: string;
    studentEmail: string;
    personalEmail: string;
    universityYear: UniversityYear;
    groupNum: string;
    image: any;

    constructor(id: number, firstName: string, lastName: string, studentEmail: string, personalEmail: string, universityYear: UniversityYear, groupNum: string, image : any) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentEmail = studentEmail;
        this.personalEmail = personalEmail;
        this.universityYear = universityYear;
        this.groupNum = groupNum;
        this.image = image;
    }
}