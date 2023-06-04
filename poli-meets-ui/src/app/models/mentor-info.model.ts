import { Subject } from "./subject-simple.model";
import { Skill } from "./skill.model";
import { Student } from "./student.model";

export class MentorInfo {
    id: number;
    description: string;
    skills: Skill[];
    subjects: Subject[];
    student: Student | undefined;

    constructor(id: number, description: string, skills: Skill[], subjects: Subject[], student : Student | undefined) {
        this.id = id;
        this.description = description;
        this.skills = skills;
        this.subjects = subjects;
        this.student = student;
    }
}
