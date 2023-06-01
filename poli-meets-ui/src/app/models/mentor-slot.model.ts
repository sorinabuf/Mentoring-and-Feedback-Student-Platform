import { MeetingType } from "./meeting-type.model";

export class MentorSlot {
    image: string;
    name: string;
    email: string;
    date: string;
    type: MeetingType;

    constructor(image: string, name: string, email: string, date: string, type: MeetingType) {
        this.image = image;
        this.name = name;
        this.email = email;
        this.date = date;
        this.type = type;
    }
}