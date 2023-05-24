

// ------------------------------------ CURRENTLY NOT USED ------------------------------------------- //


//package com.example.springbackendv4.repository;
//
//import com.example.springbackendv4.model.TrashMaster;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface TrashMasterRepository extends JpaRepository<TrashMaster, Long> {
//
//    @Query(value = "SELECT originalPath FROM TrashMaster WHERE trashFolderPath = :trashFilePath LIMIT 1", nativeQuery = true)
//    String findOriginalFilePathByTrashPath(@Param("trashFilePath") String trashFilePath);
//
//    @Query(value = "DELETE t from TrashMaster t WHERE t.trashFolderPath =: trashFilePath", nativeQuery = true)
//    void deleteByTrashFilePath(@Param("trashFilePath") String trashFilePath);
//
//    @Query(value = "DELETE t from TrashMaster t", nativeQuery = true)
//    void deleteAll();
//}
