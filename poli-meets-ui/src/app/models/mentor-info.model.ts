import { Subject } from "./subject-simple.model";
import { Skill } from "./skill.model";

export class MentorInfo {
    id: number;
    description: string;
    skills: Skill[];
    subjects: Subject[];

    constructor(id: number, description: string, skills: Skill[], subjects: Subject[]) {
        this.id = id;
        this.description = description;
        this.skills = skills;
        this.subjects = subjects;
    }
}
