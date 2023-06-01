import { MeetingSlot } from "./meeting-slot.model";
import { MeetingType } from "./meeting-type.model";
import { MentorSubject } from "./mentor-subject.model";
import { Student } from "./student.model";
export class Meeting {
    id: number;
    description: string;
    status: string;
    meetingSlot: MeetingSlot;
    student: Student;
    mentorSubject: MentorSubject;
    type: MeetingType;
    mentor: Student;

    constructor(id: number, description: string, status: string, meetingSlot: MeetingSlot, student: Student, mentorSubject: MentorSubject, type: MeetingType, mentor: Student) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.meetingSlot = meetingSlot;
        this.student = student;
        this.mentorSubject = mentorSubject;
        this.type = type;
        this.mentor = mentor;
    }
}
