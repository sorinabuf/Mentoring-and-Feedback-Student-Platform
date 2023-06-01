export class MeetingSlot {
    id: number;
    date: string;
    status: string;
    mentorId: number;

    constructor(id: number, date: string, status: string, mentorId: number) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.mentorId = mentorId;
    }
}
