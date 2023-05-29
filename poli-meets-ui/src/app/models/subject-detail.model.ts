import { UniversityYear } from "./university-year.model";

export class SubjectDetail {
    id: number;
    name: string;
    abbreviation: string;
    universityYear: UniversityYear;

    constructor(id: number, name: string, abbreviation: string, universityYear: UniversityYear) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.universityYear = universityYear;
    }
}
