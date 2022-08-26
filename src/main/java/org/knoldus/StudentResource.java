package org.knoldus;

import io.quarkus.panache.common.Sort;
import org.knoldus.entity.StudentEntity;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

 @Path("/person")
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
 public class StudentResource {

        @GET
        public List<StudentEntity> getAll() throws Exception {
            return StudentEntity.findAll(Sort.ascending("last_name")).list();
        }
        @POST
        @Transactional
        public Response create(StudentEntity student) {
            if (student == null || student.id != null)
                throw new WebApplicationException("id != null");
            student.persist();
            return Response.ok(student).status(200).build();
        }
        @PUT
        @Transactional
        @Path("{id}")
        public StudentEntity update(@PathParam("id") Long id, StudentEntity student) {
            StudentEntity entity = StudentEntity.findById(id);
            if (entity == null) {
             throw new WebApplicationException("Person with id " + id + " does not exist.", 404);
            }
            if(student.firstName != null )  entity.firstName = student.firstName;
            if(student.lastName != null)    entity.lastName = student.lastName;
            return entity;
        }
        @DELETE
        @Path("{id}")
        @Transactional
        public Response delete(@PathParam("id") Long id) {
            StudentEntity student = StudentEntity.findById(id);
            if (student == null) {
                throw new WebApplicationException("Person with id of " + id + " does not exist.", 404);
            }
            student.delete();
            return Response.status(204).build();
        }
 }

