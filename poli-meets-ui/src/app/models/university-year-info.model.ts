import { Faculty } from "./faculty.model";

export class UniversityYear {
    id: number;
    year: string;
    series: string;
    faculty: Faculty;

    constructor(id: number, year: string, series: string, faculty: Faculty) {
        this.id = id;
        this.year = year;
        this.series = series;
        this.faculty = faculty;
    }
}
