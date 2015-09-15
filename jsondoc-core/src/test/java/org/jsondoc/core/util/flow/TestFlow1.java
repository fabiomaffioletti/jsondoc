package org.jsondoc.core.util.flow;

import org.jsondoc.core.annotation.flow.ApiFlow;
import org.jsondoc.core.annotation.flow.ApiFlowSet;
import org.jsondoc.core.annotation.flow.ApiFlowStep;

@ApiFlowSet
public class TestFlow1 {
	
	@ApiFlow(
			name = "Flow1",
			description = "Description for flow 1",
			steps = {
					@ApiFlowStep(apimethodid = "M1"),
					@ApiFlowStep(apimethodid = "M2"),
					@ApiFlowStep(apimethodid = "M3")
			}
		)
	public void flow1() {
		
	}
	
	@ApiFlow(
			name = "Flow2",
			description = "Description for flow 2",
			steps = {
					@ApiFlowStep(apimethodid = "M0"),
					@ApiFlowStep(apimethodid = "M4"),
					@ApiFlowStep(apimethodid = "M5")
			}
		)
	public void flow2() {
		
	}

}
