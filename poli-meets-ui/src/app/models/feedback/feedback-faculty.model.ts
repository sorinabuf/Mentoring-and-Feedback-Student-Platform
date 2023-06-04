import {FeedbackUniversityYear} from "./feedback-university-year.model";

export class FeedbackFaculty {
    id?: number;

    name?: string;

    domain?: string;

    description?: string;

    years?: FeedbackUniversityYear[];
}