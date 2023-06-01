export class Student {
    id: number;
    firstName: string;
    lastName: string;
    studentEmail: string;
    personalEmail: string;
    universityYearId: number;
    image: any;

    constructor(id: number, firstName: string, lastName: string, studentEmail: string, personalEmail: string, universityYearId: number, image : any) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentEmail = studentEmail;
        this.personalEmail = personalEmail;
        this.universityYearId = universityYearId;
        this.image = image;
    }
}