import {FeedbackComment} from "./feedback-comment.model";
import {RatingBreakdown} from "./rating-breakdown.model";

export class FeedbackCategory {
    categoryName?: string;

    gradeCategory?: number;

    gradeQuestion?: string;

    countFeedbacks?: number;

    feedbackComments?: FeedbackComment[];

    ratingBreakdown?: RatingBreakdown[];
}