import {Semester} from "./semester.model";
import {Teacher} from "./teacher.model";
import {UniversityYear} from "./university_year.model";

export class Subject {
    id: number;
    name: string;
    abbreviation: string;
    description: string;
    semester: Semester;
    teacher: Teacher;
    universityYear: UniversityYear;

    constructor(id: number, name: string, abbreviation: string, description: string, semester: Semester, teacher: Teacher, universityYear: UniversityYear) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.semester = semester;
        this.teacher = teacher;
        this.universityYear = universityYear;
    }
}
