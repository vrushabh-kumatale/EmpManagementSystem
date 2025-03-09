package com.blogs.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogs.custom_exceptions.ResourceNotFoundException;
import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.TaskRespDto;
import com.blogs.pojos.File;
import com.blogs.pojos.Task;
import com.blogs.pojos.TaskStatus;
import com.blogs.repository.FileRepository;
import com.blogs.repository.PersonRepository;
import com.blogs.repository.TaskRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class TaskServiceImpl implements TaskService{

	@Autowired
    private TaskRepository taskRepository;
   
	@Autowired
    private FileRepository fileRepository;

    @Autowired
	private PersonRepository personRepository;
   
    @Autowired
    private ModelMapper modelMapper;
    
    private final String uploadDir = "C:\\Users\\intel\\Desktop\\front\\files"; // Configure the base upload directory
    private final Path root = Paths.get("C:\\Users\\intel\\Desktop\\front\\files");
	
	@Override
	public List<TaskRespDto> getAllTask(Long employeeId) {
	
		 return taskRepository.findByEmployee_Id(employeeId).stream()
                 .map(task -> {
                    TaskRespDto dto = modelMapper.map(task, TaskRespDto.class);
                     if (task.getManager() != null) {
                         dto.setManagerName(task.getManager().getFirstName() + " " + task.getManager().getLastName());
                         dto.setManagerId(task.getManager().getId());
                     }
                     return dto;
                 })
                 .collect(Collectors.toList());
	}

	@Override
	public Task getTaskById(Long taskId) {
		return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
	}

	@Override
	public ApiResponse updateTask(Long taskId, @Valid MultipartFile file) throws IOException {
		Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
		
		if(file != null && !file.isEmpty()){
            String originalFilename = file.getOriginalFilename();
            String fileType = file.getContentType();
            long fileSize = file.getSize();
            String uniqueFilename = generateUniqueFilename(originalFilename);
            String subDirectory = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Path uploadPath = Paths.get(uploadDir, subDirectory);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(uniqueFilename);
            file.transferTo(filePath);
            String fileLocation = filePath.toString();


            File fileRecord = new File();
            fileRecord.setFileName(originalFilename);
            fileRecord.setFileType(fileType);
            fileRecord.setFileSize(fileSize);
            fileRecord.setFileLocation(fileLocation);
            fileRecord.setUploadDateTime(LocalDateTime.now());

           File savedFile = fileRepository.save(fileRecord);
           task.setFile(savedFile);
        }
        task.setStatus(TaskStatus.PENDING_APPROVAL);
         taskRepository.save(task);
        return new ApiResponse("Task updated successfully");
	}
	
	 private String generateUniqueFilename(String originalFilename) {
         String uuid = UUID.randomUUID().toString();
         String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
         String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
         return timestamp+ "_"+ uuid + extension;
     }


	@Override
	public ApiResponse approveRequest(Long id) {
		Task task=taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid  id not present"));
		task.setStatus(TaskStatus.COMPLETED);
		taskRepository.save(task);
		return new ApiResponse("Task Accepted Succesfully");
	}

	@Override
	public ApiResponse rejectRequest(Long id) {
		Task task=taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid  id not present"));
		task.setStatus(TaskStatus.REJECTED);
		taskRepository.save(task);
		return new ApiResponse("Task Rejected Succesfully");
	}

	@Override
	public Resource load(Long taskId) {
		Task task = taskRepository.findById(taskId)
	              .orElseThrow(() -> new IllegalArgumentException("Task not found!"));
	      if (task.getFile() == null) {
	        throw new IllegalArgumentException("No file attached to this task!");
	      }
	      //get file date and link to resource
	      try {
	          Path file = root.resolve(task.getFile().getFileLocation());
	       
	          Resource resource = new UrlResource(file.toUri());
	          System.out.println(resource.exists());
	          
	          if (resource.exists() || resource.isReadable()) {
	            return resource;
	          } else {
	            throw new RuntimeException("Could not read the file!");
	          }
	        } catch (MalformedURLException e) {
	          throw new RuntimeException("Error: " + e.getMessage());
	        }
	}

}
