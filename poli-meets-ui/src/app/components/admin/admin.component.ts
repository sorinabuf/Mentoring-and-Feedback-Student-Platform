import {Component, ViewEncapsulation} from '@angular/core';
import {Faculty} from "../../models/faculty.model";
import {CoreService} from "../../services/core.service";


@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.scss'],
    encapsulation: ViewEncapsulation.None
})

export class AdminComponent {

    displayedColumnsFaculty: string[] = ['name', 'domain', 'description'];
    faculties: Faculty[] = [];

    constructor(private coreService: CoreService) {}

    ngOnInit() {
        this.coreService.get_all_faculties().subscribe(faculties => {
            this.faculties = faculties;
        })
    }
}
