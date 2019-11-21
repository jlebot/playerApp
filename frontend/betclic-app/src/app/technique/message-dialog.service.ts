import {Injectable} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap/modal/modal-ref';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class MessageDialogService {

    constructor(private ngbModalService: NgbModal) {
    }

    public open(content: any, options?: NgbModalOptions): NgbModalRef {
        let localOptions: NgbModalOptions = options;
        if (localOptions == null) {
            localOptions = {};
        }
        if (localOptions.backdrop == null) {
            localOptions.backdrop = 'static';
        }
        if (localOptions.centered == null) {
            localOptions.centered = true;
        }
        return this.ngbModalService.open(content, localOptions);
    }
}
