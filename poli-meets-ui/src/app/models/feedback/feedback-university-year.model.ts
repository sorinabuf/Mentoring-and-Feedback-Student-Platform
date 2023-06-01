import {FeedbackUniversityClass} from "./feedback-university-class.model";

export class FeedbackUniversityYear {
    id?: number;

    year?: string;

    series?: string;

    universityClasses?: FeedbackUniversityClass[];
}