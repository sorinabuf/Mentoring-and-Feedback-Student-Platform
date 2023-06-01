import { UniversityClass } from "./university-class.model";

export class MentorSubject {
    id: number;
    universityClass: UniversityClass;
    mentorId: number;

    constructor(id: number, universityClass: UniversityClass, mentorId: number) {
        this.id = id;
        this.universityClass = universityClass;
        this.mentorId = mentorId;
    }
}
