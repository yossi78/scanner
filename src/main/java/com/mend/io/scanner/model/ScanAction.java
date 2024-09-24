package com.mend.io.scanner.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mend.io.scanner.data_layer.Scan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize
@Builder
public class ScanAction {

    private ScanActionType scanActionType;
    private Scan scan;
}
