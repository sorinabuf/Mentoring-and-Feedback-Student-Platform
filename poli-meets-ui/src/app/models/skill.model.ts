export class Skill {
    id: number;
    name: string;
    abbreviation: string;
    description: string;
    semester: string;
    universityYearId: number;

    constructor(id: number, name: string, abbreviation: string, description: string, semester: string, universityYearId: number) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.semester = semester;
        this.universityYearId = universityYearId;
    }

    equals(other: Skill): boolean {
        return this.id === other.id;
    }
}
