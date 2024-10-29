package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.AttendentRequest;
import dev.chinhcd.backend.dtos.responses.AttendentResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;

import java.util.List;

public interface AttendentService {
    AttendentResponse createAttendent(AttendentRequest request) throws DataNotFoundException;

    AttendentResponse updateAttendent(AttendentRequest request) throws DataNotFoundException;

    List<AttendentResponse> getAllAttendents();

    List<AttendentResponse> searchAttendent(AttendentRequest request);

    void deleteAttendent(int id);
}
