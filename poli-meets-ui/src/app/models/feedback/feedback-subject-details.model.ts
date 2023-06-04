import {Teacher} from "../teacher.model";
import {FeedbackCategory} from "./feedback-category.model";

export class FeedbackSubjectDetails {
    id?: number;

    name?: string;

    abbreviation?: string;

    description?: string;

    semester?: string;

    teacher?: Teacher;

    gradeOverall?: number;

    countFeedbacks?: number;

    feedbackCategories?: FeedbackCategory[];
}