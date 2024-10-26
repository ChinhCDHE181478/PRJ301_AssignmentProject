package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.AttendentRequest;
import dev.chinhcd.backend.dtos.responses.AttendentResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;

import java.util.Set;

public interface AttendentService {
    AttendentResponse createAttendent(AttendentRequest request) throws DataNotFoundException;

    AttendentResponse updateAttendent(AttendentRequest request) throws DataNotFoundException;

    Set<AttendentResponse> getAllAttendents();

    Set<AttendentResponse> searchAttendent(AttendentRequest request);

    void deleteAttendent(int id);
}
