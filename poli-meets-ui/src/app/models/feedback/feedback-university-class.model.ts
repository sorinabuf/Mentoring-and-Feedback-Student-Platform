import {Teacher} from "../teacher.model";

export class FeedbackUniversityClass {
    id?: number;

    name?: string;

    abbreviation?: string;

    description?: string;

    semester?: string;

    teacher?: Teacher;

    gradeOverall?: number;
}