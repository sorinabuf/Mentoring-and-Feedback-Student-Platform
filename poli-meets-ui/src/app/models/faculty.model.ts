export class Faculty {
    id: number;
    name: string;
    domain: string;
    description: string;

    constructor(id: number, name: string, domain: string, description: string) {
        this.id = id;
        this.name = name;
        this.domain = domain;
        this.description = description;
    }
}
