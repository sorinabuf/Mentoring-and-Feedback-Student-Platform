import { MeetingSlotStatus } from "./meeting-slot-status.model";

export class MeetingSlot {
    id: number;
    date: string;
    status: MeetingSlotStatus;
    mentorId: number;

    constructor(id: number, date: string, status: MeetingSlotStatus, mentorId: number) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.mentorId = mentorId;
    }
}
