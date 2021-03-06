/**
 * (c) Copyright 2012 WibiData, Inc.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kiji.schema.shell.ddl

import scala.collection.JavaConversions._

import org.kiji.schema.avro.TableLayoutDesc

import org.kiji.schema.shell.DDLException
import org.kiji.schema.shell.Environment

/** Add a group-type family to a table. */
class AlterTableAddGroupFamilyCommand(
    val env: Environment,
    val tableName: String,
    val groupClause: LocalityGroupProp,
    val localityGroupName: String) extends TableDDLCommand {

  override def validateArguments(): Unit = {
    val layout = getInitialLayout()
    if (groupClause.property != LocalityGroupPropName.GroupFamily) {
      // Getting here would mean an error in DDLParser, or in a manually-formed instance.
      throw new DDLException("Expected group-type family clause locality group property.")
    }
    checkColFamilyMissing(layout, groupClause.value.asInstanceOf[GroupFamilyInfo].name)
    checkLocalityGroupExists(layout, localityGroupName)
  }

  override def updateLayout(layout: TableLayoutDesc): Unit = {
    // Add the new group-type column family to the locality group.
    layout.getLocalityGroups().foreach { localityGroup =>
      if (localityGroup.getName().equals(localityGroupName)) {
        groupClause.apply(localityGroup)
      }
    }
  }
}
