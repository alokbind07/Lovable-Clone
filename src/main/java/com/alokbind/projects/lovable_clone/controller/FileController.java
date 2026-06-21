package com.alokbind.projects.lovable_clone.controller;

import com.alokbind.projects.lovable_clone.dto.project.FileContentResponse;
import com.alokbind.projects.lovable_clone.dto.project.FileNode;
import com.alokbind.projects.lovable_clone.dto.project.FileTreeResponse;
import com.alokbind.projects.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
public class FileController {

    private final ProjectFileService projectFileService;

    @GetMapping
    public ResponseEntity<FileTreeResponse> getFileTree(@PathVariable Long projectId){
        return ResponseEntity.ok(projectFileService.getFileTree(projectId));
    }

    @GetMapping("/content")
    public ResponseEntity<FileContentResponse> getFile(
            @PathVariable Long projectId,
            @RequestParam String path) {
        return ResponseEntity.ok(projectFileService.getFileContent(projectId, path));
    }

}
