import {Year} from "./year.model";

export class UniversityYear {
    id: number;
    year: Year;
    series: String;
    facultyId: number;

    constructor(id: number, year: Year, series: String, facultyId: number) {
        this.id = id;
        this.year = year;
        this.series = series;
        this.facultyId = facultyId;
    }
}