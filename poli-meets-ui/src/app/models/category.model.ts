export class Category {

    id?: number;

    name?: string;

    feedbackTextQuestion?: string;

    gradeQuestion?: string;


    constructor(id: number | undefined, name: string | undefined,
                feedbackTextQuestion: string | undefined, gradeQuestion: string | undefined) {
        this.id = id;
        this.name = name;
        this.feedbackTextQuestion = feedbackTextQuestion;
        this.gradeQuestion = gradeQuestion;
    }
}