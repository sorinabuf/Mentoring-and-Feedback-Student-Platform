export class UniversityYear {
    id: number;
    year: string;
    series: string;
    facultyId: number;

    constructor(id: number, year: string, series: string, facultyId: number) {
        this.id = id;
        this.year = year;
        this.series = series;
        this.facultyId = facultyId;
    }
}
