import { Semester } from "./semester.model";

export class UniversityClass {
    id: number;
    name: string;
    abbreviation: string;
    description: string;
    semester: Semester;
    universityYearId: number;

    constructor(id: number, name: string, abbreviation: string, description: string, semester: Semester, universityYearId: number) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.semester = semester;
        this.universityYearId = universityYearId;
    }
}