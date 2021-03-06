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

import org.kiji.schema.avro.TableLayoutDesc
import org.kiji.schema.shell.Environment

class DropTableCommand(val env: Environment, val tableName: String) extends TableDDLCommand {

  override def validateArguments(): Unit = {
    checkTableExists()
  }

  // Don't make any changes to the layout itself.
  override def updateLayout(layout: TableLayoutDesc): Unit = { }

  override def applyUpdate(layout: TableLayoutDesc): Unit = {
    // We apply the layout by calling the drop table operation of KijiAdmin.
    env.kijiSystem.dropTable(getKijiInstance(), tableName)
  }
}
