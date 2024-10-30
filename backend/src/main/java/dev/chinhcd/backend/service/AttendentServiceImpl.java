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
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendentServiceImpl implements AttendentService {
    private final EntityManager entityManager;
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

    @Transactional
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

    @Transactional(readOnly = true)
    @Override
    public List<AttendentResponse> getAllAttendents() {
        return attendentRepository.findAll().stream()
                .map(this::convertToAttendentResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AttendentResponse> searchAttendent(AttendentRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Attendent> cq = cb.createQuery(Attendent.class);
        Root<Attendent> attendentRoot = cq.from(Attendent.class);

        List<Predicate> predicates = new ArrayList<>();

        // Điều kiện tìm kiếm theo workerId
        if (request.workerScheduleRequest().workerId() != null) {
            predicates.add(cb.equal(attendentRoot.get("workerSchedule").get("workerScheduleId"), request.workerScheduleRequest().workerId()));
        }

        // Điều kiện tìm kiếm theo scheduleId
        if (request.workerScheduleRequest().scheduleId() != null) {
            predicates.add(cb.equal(attendentRoot.get("workerSchedule").get("schedule").get("id"), request.workerScheduleRequest().scheduleId()));
        }

        // Điều kiện tìm kiếm theo employeeId
        if (request.workerScheduleRequest().employeeRequest().id() != null) {
            predicates.add(cb.equal(attendentRoot.get("workerSchedule").get("employee").get("id"), request.workerScheduleRequest().employeeRequest().id()));
        }

        // Ghép các điều kiện lại với nhau
        cq.where(predicates.toArray(new Predicate[0]));

        // Thực hiện truy vấn
        List<Attendent> results = entityManager.createQuery(cq).getResultList();

        return results.stream().map(this::convertToAttendentResponse).toList();
    }

    @Transactional
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
