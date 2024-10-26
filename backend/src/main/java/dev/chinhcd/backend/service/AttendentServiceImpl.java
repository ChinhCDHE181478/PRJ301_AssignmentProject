package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.AttendentRequest;
import dev.chinhcd.backend.dtos.responses.AttendentResponse;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.dtos.responses.WorkerScheduleResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Attendent;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.models.ScheduleCampaign;
import dev.chinhcd.backend.models.WorkerSchedule;
import dev.chinhcd.backend.repository.AttendentRepository;
import dev.chinhcd.backend.service.InterfaceService.AttendentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendentServiceImpl implements AttendentService {
    private final AttendentRepository attendentRepository;
    private final WorkerServiceImpl workerService;
    private final EmployeeServiceImpl employeeService;
    private final ScheduleServiceImpl scheduleService;

    @Transactional
    @Override
    public AttendentResponse createAttendent(AttendentRequest request) throws DataNotFoundException {

        ScheduleCampaign scheduleCampaign = scheduleService.getScheduleCampaign(request.workerScheduleRequest().scheduleId())
                .orElseThrow(() -> new DataNotFoundException("Schedule campaign not found"));

        Employee employee = employeeService.getEmployeeById(request.workerScheduleRequest().employeeRequest().id())
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));

        Attendent attendent = attendentRepository.save(Attendent.builder()
                .workerSchedule(WorkerSchedule.builder()
                        .schedule(scheduleCampaign)
                        .employee(employee)
                        .quantity(request.workerScheduleRequest().quantity())
                        .build())
                .quantity(request.quantity())
                .alpha(request.alpha())
                .build());

        return convertToAttendentResponse(attendent);
    }

    @Override
    public AttendentResponse updateAttendent(AttendentRequest request) throws DataNotFoundException {

        Attendent attendent = attendentRepository.findById(request.attendentId())
                .orElseThrow(() -> new DataNotFoundException("Attendent not found"));

        WorkerSchedule workerSchedule = workerService.getWorker(request.workerScheduleRequest().workerId())
                .orElseThrow(() -> new DataNotFoundException("Worker schedule not found"));

        ScheduleCampaign scheduleCampaign = scheduleService.getScheduleCampaign(request.workerScheduleRequest().scheduleId())
                .orElseThrow(() -> new DataNotFoundException("Schedule campaign not found"));

        Employee employee = employeeService.getEmployeeById(request.workerScheduleRequest().employeeRequest().id())
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));

        workerSchedule.setSchedule(scheduleCampaign);
        workerSchedule.setEmployee(employee);
        workerSchedule.setQuantity(request.workerScheduleRequest().quantity());

        attendent.setWorkerSchedule(workerSchedule);
        attendent.setQuantity(request.quantity());
        attendent.setAlpha(request.alpha());

        Attendent attendentSaved = attendentRepository.save(attendent);

        return convertToAttendentResponse(attendentSaved);

    }

    @Override
    public Set<AttendentResponse> getAllAttendents() {
        return attendentRepository.findAll().stream()
                .map(this::convertToAttendentResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<AttendentResponse> searchAttendent(AttendentRequest request) {
        return Set.of();
    }

    @Override
    public void deleteAttendent(int id) {
        attendentRepository.deleteById(id);
    }

    private AttendentResponse convertToAttendentResponse(Attendent attendent) {
        return AttendentResponse.builder()
                .attendentId(attendent.getId())
                .workerScheduleResponse(WorkerScheduleResponse.builder()
                        .workerId(attendent.getWorkerSchedule().getWorkerScheduleId())
                        .employeeResponse(EmployeeResponse.builder()
                                .id(attendent.getWorkerSchedule().getEmployee().getEmployeeId())
                                .employeeName(attendent.getWorkerSchedule().getEmployee().getEmployeeName())
                                .build())
                        .quantity(attendent.getWorkerSchedule().getQuantity())
                        .scheduleId(attendent.getWorkerSchedule().getSchedule().getScheduleId())
                        .build())
                .quantity(attendent.getQuantity())
                .alpha(attendent.getAlpha())
                .build();
    }
}
