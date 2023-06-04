export class MeetingRequest {
    id: number;
    description: string;
    status: string;
    meetingSlotId: number;
    studentId: number;
    mentorSubjectId: number;

    constructor(id: number, description: string, status: string, meetingSlotId: number, studentId: number, mentorSubjectId: number) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.meetingSlotId = meetingSlotId;
        this.studentId = studentId;
        this.mentorSubjectId = mentorSubjectId;
    }
}