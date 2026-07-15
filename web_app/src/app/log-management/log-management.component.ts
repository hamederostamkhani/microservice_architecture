import {Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {Node} from "../model/node.model";
import {ApiCallService} from "../service/api-call.service";
import {FlowFilter} from "../model/flow-filter.model";
import {Flow} from "../model/flow.model";

@Component({
  selector: 'app-log-management',
  templateUrl: './log-management.component.html',
  styleUrls: ['./log-management.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class LogManagementComponent implements OnInit {

  dataSource: DisplayableFlow[] = [];
  columnsToDisplay = ['uid', 'startTime', 'endTime', 'requestName'];
  expandedElement: DisplayableFlow | null = null;
  flowFilter: FlowFilter = new FlowFilter();

  constructor(private apiCallService: ApiCallService) {
  }

  ngOnInit(): void {
    this.flowFilter.page = 0;
    this.flowFilter.npp = 10;
    this.apiCallService.getCorrelatedLogs(this.flowFilter).subscribe(result => {
      console.log(result);
      this.flowFilter.totalItem = result.totalItems;
      let displayableFlows: DisplayableFlow[] = [];
      result.response.forEach(flow => {
        const nodes: Node[] = Object.assign([], flow.nodes)
        let displayableFlow: DisplayableFlow = {
          uid: flow.uid,
          nodes: flow.nodes,
          startTime: flow.nodes[0].time,
          endTime: flow.nodes[flow.nodes.length - 1].time,
          requestName: flow.nodes[0].flowStart
        };
        displayableFlows.push(displayableFlow);
      });
      this.dataSource = displayableFlows;
    });
  }

}

export interface DisplayableFlow {
  uid: string,
  startTime: string,
  endTime: string,
  requestName: string,
  nodes: Node[];
}
